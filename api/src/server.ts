import express from 'express'
import cors from "cors"
import router from './routes'
import swaggerUi from 'swagger-ui-express'
import swaggerConfig from './swagger.json'

const app = express()

const PORT = 3333

app.use(express.json())
app.use(cors())

app.use("/flightapp", router)


app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerConfig));


app.listen(PORT, () => {
    console.log("main app is running!")
})