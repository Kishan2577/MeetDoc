import mongoose from "mongoose";

const slotSchema = mongoose.Schema(
  {
    doctorId: {
      type: mongoose.Schema.Types.ObjectId,
      ref: "Doctor",
      required: true,
    },
    date: {
      type: String, // Format: "YYYY-MM-DD"
      required: true,
    },
    startTime: {
      type: String, // e.g., "13:00"
      required: true,
    },
    endTime: {
      type: String, // e.g., "14:00"
      required: true,
    },
    limit: {
      type: Number,
      default: 5,
    },
    booked: {
      type: Number,
      default: 0,
    },
    remaining: {
      type: Number,
      default: function () {
        return this.limit;
      },
    },
    notes: {
      type: String,
    },
  },
  { timestamps: true }
);

const Slot = mongoose.model("Slot", slotSchema);
export default Slot;
