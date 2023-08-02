import mongoose from "mongoose";

export const connectMDB = () => {
    mongoose.connect(process.env.CONNECTION_STRING, {
        useNewUrlParser: true,
        useUnifiedTopology: true
    }).then(() => {
        console.log("DB Connected successfully");
    }).catch((e) => {
        console.log("failed");
    });
}