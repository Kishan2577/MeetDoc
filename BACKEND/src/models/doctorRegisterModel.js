import mongoose from "mongoose";
import bcrypt from "bcryptjs";

const doctorSchema = mongoose.Schema(
  {
    fullName: { type: String, required: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    role: { 
        type: String, 
        enum: ["patient", "doctor"],
        default: "doctor"
    },
    medicalRegNumber: { type: String, required: true },
    stateMedicalCouncil: { type: String, required: true },
    specialization: { type: String, required: true },
    medicalCollege: { type: String, required: true },
    yearOfRegistration: { type: Number, required: true },
    hospitalClinicName: { type: String, required: true },
    phoneNumber: { type: String, required: true },

    // 👇 New fields added
    patientsChecked: { type: Number, default: 0 },
    yearsOfExperience: { type: Number, default: 0 },
    rating: { type: Number, min: 0, max: 5, default: 0 },
    about: { type: String, default: "" },
    imageUrl: { type: String, default: "" }
  },
  { timestamps: true }
);

// Password hashing
doctorSchema.pre("save", async function (next) {
  if (!this.isModified("password")) return next();
  this.password = await bcrypt.hash(this.password, 10);
  next();
});

// Password comparison
doctorSchema.methods.matchPassword = async function (enteredPassword) {
  return await bcrypt.compare(enteredPassword, this.password);
};

const Doctor = mongoose.model("Doctor", doctorSchema);
export default Doctor;
