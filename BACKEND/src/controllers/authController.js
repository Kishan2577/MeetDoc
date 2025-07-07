import Patient from "../models/patientRegisterModel.js";
import generateToken from "../utils/generateToken.js";
import Doctor from "../models/doctorRegisterModel.js";
// Register a new patient
export const registerUser = async (req, res) => {
  try {
    const { name, email, password, address, age, sex, occupation, maritalStatus, contactInfo,role} = req.body;

    const userExists = await Patient.findOne({ email });
    if (userExists) return res.status(400).json({ message: "User already exists" });

    const user = await Patient.create({ name, email, password, address, age, sex, occupation, maritalStatus, contactInfo,role });

    if (user) {
      res.status(201).json({
        _id: user._id,
        status: true
      });
    } else {
      console.log("400 HERE");
      
      res.status(400).json({ message: "Invalid user data" });
    }
  } catch (error) {
    
    console.log(error);
    
    res.status(500).json({ message: "Server Error", error: error.message });
  }
};

// Login User
export const loginUser = async (req, res) => {
  try {
    const { email, password, role } = req.body;

    if (!role || !["patient", "doctor"].includes(role)) {
      console.log("HERE 0");
      
      return res.status(400).json({ message: "Invalid role. Choose either 'patient' or 'doctor'." });
    }

    let user;

    if (role === "patient") {
      user = await Patient.findOne({ email });
    } else if (role === "doctor") {
      user = await Doctor.findOne({ email });
    }

    if (!user) {
      console.log(" HERE 1");
      
      return res.status(401).json({ message: "Invalid email or password" });
    }

    // Verify password
    const isMatch = await user.matchPassword(password);
    if (!isMatch) {
      console.log(" HERE 2");
      return res.status(401).json({ message: "Invalid email or password" });
    }

    // Send response with JWT token
    res.json({
      _id: user._id,
      name: role === "patient" ? user.name : user.fullName,
      email: user.email,
      role: user.role,
      
      token: generateToken(user._id),
    });
  } catch (error) {
    console.log(" HERE 3");
    res.status(500).json({ message: "Server Error", error: error.message });
  }
};