import Doctor from "../models/doctorRegisterModel.js";

export const getDoctorById = async (req, res) => {
  try {
    const { _id } = req.body;

    if (!_id) {
      return res.status(400).json({ message: "Doctor ID is required" });
    }

    const doctor = await Doctor.findById(_id).select("-password");

    if (!_id) {
      return res.status(404).json({ message: "Doctor not found" });
    }
    console.log(doctor);
    
    res.status(200).json({ success: true, doctor });
  } catch (error) {
    res.status(500).json({ message: "Server Error", error: error.message });
  }
};
