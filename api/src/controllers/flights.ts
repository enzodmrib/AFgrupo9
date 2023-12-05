import { flightsApi } from "../libs/axios";

export async function getFlightLocations(req, res) {
  try {
    const places = await flightsApi.get('/places', {
      headers: {
        'user-id': req.headers["user-id"]
      }
    })

    return res.status(200).json({ places: places.data.places })
  } catch (e) {
    return res.status(500).json({ message: String(e) })
  }

}