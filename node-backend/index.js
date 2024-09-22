const express = require('express')
const app = express()
const PORT = 3000
const postgres = require('postgres')

// Initialize connection to PostgreSQL (define sql once at the top)
const sql = postgres('postgres://fitnessapp:test@localhost:5432/fitness')

// Fetch all sessions
async function getSessions() {
  const sessions = await sql`SELECT * FROM session`
  return sessions
}

// Fetch all exercises
async function getExercises() {
  const exercises = await sql`SELECT * FROM exercise`
  return exercises
}

// Fetch distinct muscle groups
async function getMuscles() {
  const muscles = await sql`SELECT DISTINCT muscle FROM exercise`
  return muscles
}

// Fetch exercises based on muscle group
async function getExercisesForMuscle(muscle) {
  const exercises = await sql`SELECT * FROM exercise WHERE muscle = ${muscle}`
  return exercises
}

// Route to get all sessions
app.get('/get-sessions', async (req, res) => {
  try {
    const sessions = await getSessions()
    res.json(sessions)
  } catch (err) {
    console.error(err)
    res.status(500).send('Server error')
  }
})

// Route to get all exercises
app.get('/get-excercises', async (req, res) => {
  try {
    const exercises = await getExercises()
    res.json(exercises)
  } catch (err) {
    console.error(err)
    res.status(500).send('Server error')
  }
})

// Route to get distinct muscle groups
app.get('/get-muscles', async (req, res) => {
  try {
    const muscles = await getMuscles()
    res.json(muscles)
  } catch (err) {
    console.error(err)
    res.status(500).send('Server error')
  }
})

// Route to get exercises by muscle group
app.get('/get-exercises-by-muscle', async (req, res) => {
  const { muscle } = req.query
  if (!muscle) {
    return res.status(400).send("Missing muscle parameter")
  }
  try {
    const exercises = await getExercisesForMuscle(muscle)
    res.json(exercises)
  } catch (err) {
    console.error(err)
    res.status(500).send('Server error')
  }
})

// Start the server
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`)
})
