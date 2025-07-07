import mongoose from "mongoose";
import { DB_NAME } from "../constants.js";


const connectDB = async()=>{
    try {
        await mongoose.connect(`${process.env.MONGOURI}/${DB_NAME}`)
        console.log(`\n MONGODB CONNECTED!!!`);
        
    } catch (error) {
        console.log("MONGODB CONNECTON ERROR",error);
        process.exit(1);
    }
}


export default connectDB;