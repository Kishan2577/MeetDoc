import Appointment from "../models/appointment.js";

export const createAppointment = async (req, res) => {
    try {
        const { patient_id, doctor_id, date_of_appointment, start_time, end_time } = req.body;

        if (!patient_id || !doctor_id || !date_of_appointment || !start_time || !end_time) {
            return res.status(400).json({ message: "All fields are required" });
        }

        const url = generateFakeGoogleMeetUrl();
        const newAppointment = new Appointment({
            patient_id,
            doctor_id,
            date_of_appointment,
            start_time,
            end_time,
            url
        });

        await newAppointment.save();

        res.status(201).json({ message: "Appointment created successfully" });
    } catch (error) {
        res.status(500).json({ message: "Server error", error: error.message });
    }
};


function generateFakeGoogleMeetUrl() {
    const chars = 'abcdefghijklmnopqrstuvwxyz';
    const segment = () =>
      Array.from({ length: 3 }, () => chars[Math.floor(Math.random() * chars.length)]).join('');
  
    const url = `https://meet.google.com/${segment()}-${segment()}-${segment()}`;
    return url;
  }
  
  // Example usage:
  //console.log(generateFakeGoogleMeetUrl());
  
