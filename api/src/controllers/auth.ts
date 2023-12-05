import { eventsApi, flightsApi } from "../libs/axios"

export async function login(req, res) {
  const response = await flightsApi.post('/login', req.body).catch(e => e.response)

  return res.status(response.status).json(response.data)
}

export async function signUp(req, res) {
  const response = await flightsApi.post('/user', req.body).catch(e => e.response)

  if(response.status === 201) {
    return res.status(201).json(response.data)
  }
}