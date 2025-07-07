import Patient from "../models/patientRegisterModel.js";

export const getPatientById = async (req, res) => {
    try {
        const { patient_id } = req.body;

        if (!patient_id) {
            return res.status(400).json({ message: "patient_id is required" });
        }

        const patient = await Patient.findById(patient_id).select("-password");

        if (!patient) {
            return res.status(404).json({ message: "Patient not found" });
        }

        res.status(200).json({ message: "Patient details fetched successfully", patient });
    } catch (error) {
        res.status(500).json({ message: "Server error", error: error.message });
    }
};
