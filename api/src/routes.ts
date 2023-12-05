import express from 'express'
import { login } from './controllers/auth'
import { getFlightLocations } from './controllers/flights'

const router = express.Router()

router.post('/login', login)
router.get('/places', getFlightLocations)

export default router