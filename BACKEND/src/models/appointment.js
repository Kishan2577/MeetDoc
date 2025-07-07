import mongoose from "mongoose";

const appointmentSchema = new mongoose.Schema({
    patient_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User",
        required: true
    },
    doctor_id: {
        type: mongoose.Schema.Types.ObjectId,
        ref: "User", // or "Doctor" if doctors are a separate model
        required: true
    },
    date_of_appointment: {
        type: Date,
        required: true
    },
    start_time: {
        type: String,
        required: true
    },
    end_time: {
        type: String,
        required: true
    },
    url: {
        type: String,
        required: true
    }
}, { timestamps: true });

export default mongoose.model("Appointment", appointmentSchema);
