import { hotelsApi } from "../libs/axios";

export async function getHotels(req, res) {
  const response = await hotelsApi.get('/hotels').catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function getHotelById(req, res) {
  const response = await hotelsApi.get(`/hotels/${req.params.hotelId}`).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function checkinHotel(req, res) {
  const response = await hotelsApi.post('/hotels/checkin', req.body).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function checkoutHotel(req, res) {
  const response = await hotelsApi.post('/hotels/checkout', req.body).catch(e => e.response)

  return res.status(response.status).json(response.data)
}