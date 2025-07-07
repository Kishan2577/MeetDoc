import mongoose from "mongoose";
import bcrypt from "bcryptjs";

const patientSchema = new mongoose.Schema(
  {
    name: { type: String, required: true },
    email: { type: String, required: true, unique: true },
    password: { type: String, required: true },
    address: { type: String, required: true },
    age: { type: Number, required: true },
    sex: { type: String, required: true, enum: ["Male", "Female", "Other"] },
    occupation: { type: String, required: true },
    maritalStatus: { type: String, required: true, enum: ["Single", "Married", "Divorced", "Widowed"] },
    contactInfo: { type: String, required: true },
    role: { 
      type: String, 
      enum: ["patient", "doctor"],
      default : "patient"
    }
  },
  { timestamps: true }
);

// Hash password before saving
patientSchema.pre("save", async function (next) {
  if (!this.isModified("password")) return next();
  this.password = await bcrypt.hash(this.password, 10);
  next();
});

// Compare password
patientSchema.methods.matchPassword = async function (enteredPassword) {
  return await bcrypt.compare(enteredPassword, this.password);
};

const Patient = mongoose.model("Patient", patientSchema);

export default Patient;
