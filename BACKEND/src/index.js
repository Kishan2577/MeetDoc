import express from "express";
import connectDB from "./db/index.js";
import dotenv from "dotenv";
import authRoutes from "./routes/authRoutes.js";


const app = express()

dotenv.config({
    path:'./env'
})

app.use(express.json())
app.use("/api/v1", authRoutes);

app.get("/", (req, res) => {
    res.send("API is running...");
});
  

connectDB()
.then(()=>{
    app.listen(process.env.PORT || 8000, ()=>{
        console.log(`SERVER IS RUNNING AT PORT http://localhost:${process.env.PORT}`);
    })
})
.catch((error)=>{
    console.log("MONGODB ERROR ",error);
})

