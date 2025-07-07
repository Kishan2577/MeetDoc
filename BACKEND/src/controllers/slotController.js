import Slot from "../models/slotModel.js";
import moment from "moment";

export const createSlot = async (req, res) => {
  try {
    const { doctorId, date, startTime, endTime, limit, notes } = req.body;

    if (!doctorId || !date || !startTime || !endTime) {
      return res.status(400).json({ message: "Required fields are missing" });
    }

    const slot = await Slot.create({
      doctorId,
      date,
      startTime,
      endTime,
      limit,
      booked: 0,
      remaining: limit,
      notes,
    });

    res.status(201).json({ success: true, message: "Slot created", slot });
  } catch (error) {
    res.status(500).json({ message: "Server Error", error: error.message });
  }
};


export const getTodaySlotsByDoctor = async (req, res) => {
    try {
      const { doctorId } = req.body;
  
      if (!doctorId) return res.status(400).json({ message: "Doctor ID required" });
  
      const today = moment().format("YYYY-MM-DD");
  
      const slots = await Slot.find({ doctorId, date: today });
  
      res.status(200).json({ success: true, slots });
    } catch (error) {
      res.status(500).json({ message: "Server Error", error: error.message });
    }
  };


  export const getSlotsByDate = async (req, res) => {
    try {
      const { doctorId, date } = req.body;
  
      if (!doctorId || !date) {
        return res.status(400).json({ message: "Doctor ID and date are required" });
      }
  
      const slots = await Slot.find({ doctorId, date });
  
      if (!slots || slots.length === 0) {
        return res.status(404).json({ message: "No slots found for this doctor on the selected date",status: false });
      }
  
      res.status(200).json({ success: true, slots });
    } catch (error) {
      res.status(500).json({ message: "Server Error", error: error.message });
    }
  };
  
