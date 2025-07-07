import Doctor from "../models/doctorRegisterModel.js";

export const getAllDoctors = async (req, res) => {
  try {
    const doctors = await Doctor.find({});
    
    res.status(200).json({ success: true, doctors });
    console.log("DATA SEND");
    
  } catch (error) {
    console.log("DATA NOT SEND",error);
    res.status(500).json({ message: "Server Error", error: error.message });
  }
};
