
const express = require('express')
const app = express()
const PORT = 3000
const postgres = require('postgres')

async function getSessions() {
  const sql = postgres('postgres://fitnessapp:test@localhost:5432/fitness')
  const sessions = await sql`SELECT * FROM session`
  // Convert to json and return
  return sessions
}

async function getExcercises() {
  const sql = postgres('postgres://fitnessapp:test@localhost:5432/fitness')
  const excercises = await sql`SELECT * FROM exercise`
  // Convert to json and return
  return excercises
}
//const sql = postgres('postgres://postgres:password@localhost:5432/postgres')

app.get('/', (req, res) => {
  res.send('Hello World!')
})

app.get('/get-sessions', (req, res) => {
  getSessions().then((sessions) => {
    res.json(sessions)
  })
})

app.get('/get-excercises', (req, res) => {
  getSessions().then((excercises) => {
    res.json(excercises)
  })
})

app.get('/', (req, res) => {
  res.send('Hello World!')
})


app.listen(PORT, () => {
  console.log(`Example app listening on port ${PORT}`)
})
