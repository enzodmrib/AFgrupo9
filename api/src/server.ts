import express from 'express'
import cors from "cors"
import router from './routes'
import swaggerConfig from './swagger.json'
import { initDB } from './database/init'

const app = express()

const PORT = 3333

app.use(express.json())
app.use(cors())

initDB()

app.use("/tourismapp", router)


app.listen(PORT, () => {
    console.log("main app is running!")
})