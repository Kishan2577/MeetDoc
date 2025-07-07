import Doctor from "../models/doctorRegisterModel.js";
import generateToken from "../utils/generateToken.js";

// Register a new doctor
export const registerDoctor = async (req, res) => {
  try {
    const {
      fullName,
      email,
      password,
      medicalRegNumber,
      stateMedicalCouncil,
      specialization,
      medicalCollege,
      yearOfRegistration,
      hospitalClinicName,
      phoneNumber,
      // New fields
      patientsChecked,
      yearsOfExperience,
      rating,
      about,
      imageUrl
    } = req.body;

    // Check if the doctor already exists
    const doctorExists = await Doctor.findOne({ email });
    if (doctorExists) {
      return res.status(400).json({ message: "Doctor already exists" });
    }

    // Create new doctor
    const doctor = await Doctor.create({
      fullName,
      email,
      password,
      medicalRegNumber,
      stateMedicalCouncil,
      specialization,
      medicalCollege,
      yearOfRegistration,
      hospitalClinicName,
      phoneNumber,
      // Add new fields here
      patientsChecked,
      yearsOfExperience,
      rating,
      about,
      imageUrl
    });

    if (doctor) {
      res.status(201).json({
        _id: doctor._id,
        fullName: doctor.fullName,
        email: doctor.email,
        role: doctor.role,
        token: generateToken(doctor._id),
      });
    } else {
      res.status(400).json({ message: "Invalid doctor data" });
    }
  } catch (error) {
    res.status(500).json({ message: "Server Error", error: error.message });
  }
};
