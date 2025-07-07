import Appointment from "../models/appointment.js";

export const getAppointmentsByPatient = async (req, res) => {
    try {
        const { patient_id } = req.body;

        if (!patient_id) {
            return res.status(400).json({ message: "patient_id is required" });
        }

        const appointments = await Appointment.find({ patient_id });

        if (!appointments || appointments.length === 0) {
            return res.status(404).json({ message: "No appointments found for this patient" });
        }

        res.status(200).json({ message: "Appointments fetched successfully", appointments });
    } catch (error) {
        res.status(500).json({ message: "Server error", error: error.message });
    }
};
