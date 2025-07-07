import jwt from "jsonwebtoken";
import Patient from "../models/patientRegisterModel.js";

export const protect = async (req, res, next) => {
  try {
    let token = req.headers.authorization;

    if (!token || !token.startsWith("Bearer ")) {
      console.log("HERE 3");
      return res.status(401).json({ message: "Not authorized, token missing" });
    }

    token = token.split(" ")[1]; // Remove "Bearer" and get the token
    const decoded = jwt.verify(token, process.env.JWT_SECRET);

    req.user = await Patient.findById(decoded.id).select("-password");
    if (!req.user) {
      console.log("HERE 1");
      
      return res.status(401).json({ message: "Not authorized, invalid token" });
    }

    next();
  } catch (error) {
    console.log("HERE 2");
    res.status(401).json({ message: "Not authorized, token failed" });
  }
};
