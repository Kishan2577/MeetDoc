import express from "express";
import { registerUser, loginUser } from "../controllers/authController.js";
import { registerDoctor } from "../controllers/doctorController.js";
import { getAllDoctors } from "../controllers/allDoctorController.js";
import { protect } from "../middlewares/authMiddleware.js";
import { getDoctorById } from "../controllers/getDoctorById.js";
import { createSlot, getTodaySlotsByDoctor,getSlotsByDate } from "../controllers/slotController.js";
import { protect1 } from "../middlewares/authMiddleware2.js";
import { createAppointment } from "../controllers/appointmentController.js";
import { getAppointmentsByPatient } from "../controllers/getAppointmentController.js";
import { getPatientById } from "../controllers/patientController.js";
const router = express.Router();

router.post("/doctorRegister", registerDoctor);
router.post("/patientRegister", registerUser);
router.post("/login", loginUser);
router.get("/list",protect, getAllDoctors);
router.get("/lists",protect1, getAllDoctors);
router.post("/getDoctor", protect, getDoctorById);
router.post("/getDoctors", protect1, getDoctorById);

// Create a new slot (doctor/admin protected)
router.post("/createSlots", protect1, createSlot);


// Get today's slots for a specific doctor
router.post("/getTodaySlot", protect, getTodaySlotsByDoctor);


router.post("/by-date", protect, getSlotsByDate);

router.post("/createAppointment",protect, createAppointment);

router.post("/get-by-patient", protect, getAppointmentsByPatient); 

router.post("/get-patient", protect, getPatientById);

export default router;
