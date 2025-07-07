import jwt from "jsonwebtoken";
import Doctor from "../models/doctorRegisterModel.js";

export const protect1 = async (req, res, next) => {
  try {
    let token = req.headers.authorization;

    if (!token || !token.startsWith("Bearer ")) {
      console.log("HERE 30");
      return res.status(401).json({ message: "Not authorized, token missing" });
    }

    token = token.split(" ")[1]; // Remove "Bearer" and get the token
    const decoded = jwt.verify(token, process.env.JWT_SECRET);

    req.user = await Doctor.findById(decoded.id).select("-password");
    if (!req.user) {
      console.log("HERE 10");
      
      return res.status(401).json({ message: "Not authorized, invalid token" });
    }

    next();
  } catch (error) {
    console.log("HERE 20");
    res.status(401).json({ message: "Not authorized, token failed" });
  }
};
