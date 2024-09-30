const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const PORT = 3000;
const postgres = require('postgres');

// Initialize connection to PostgreSQL
const sql = postgres('postgres://fitnessapp:test@localhost:5432/fitness');

// Middleware to parse JSON body
app.use(bodyParser.json());

// Start the server
app.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`);
});

// Fetch all sessions
async function getSessions() {
  const sessions = await sql`SELECT * FROM session`;
  return sessions;
}

// Fetch all exercises
async function getExercises() {
  const exercises = await sql`SELECT * FROM exercise`;
  return exercises;
}

// Fetch distinct muscle groups
async function getMuscles() {
  const muscles = await sql`SELECT DISTINCT muscle FROM exercise`;
  return muscles;
}

// Fetch exercises based on muscle group
async function getExercisesForMuscle(muscle) {
  const exercises = await sql`SELECT * FROM exercise WHERE muscle = ${muscle}`;
  return exercises;
}

// Route to get all sessions
app.get('/get-sessions', async (req, res) => {
  try {
    const sessions = await getSessions();
    res.json(sessions);
  } catch (err) {
    console.error(err);
    res.status(500).send('Server error');
  }
});

// Route to get all exercises
app.get('/get-excercises', async (req, res) => {
  try {
    const exercises = await getExercises();
    res.json(exercises);
  } catch (err) {
    console.error(err);
    res.status(500).send('Server error');
  }
});

// Route to get distinct muscle groups
app.get('/get-muscles', async (req, res) => {
  try {
    const muscles = await getMuscles();
    res.json(muscles);
  } catch (err) {
    console.error(err);
    res.status(500).send('Server error');
  }
});

// Route to get exercises by muscle group
app.get('/get-exercises-by-muscle', async (req, res) => {
  const { muscle } = req.query;
  if (!muscle) {
    return res.status(400).send("Missing muscle parameter");
  }
  try {
    const exercises = await getExercisesForMuscle(muscle);
    res.json(exercises);
  } catch (err) {
    console.error(err);
    res.status(500).send('Server error');
  }
});

// Route to create a session
app.post('/create-session', async (req, res) => {
  const { sessionTitle, exercises, image_url } = req.body;
  try {
    // Insert the session name and image URL into the "session" table
    const session = await sql`
      INSERT INTO session (name, image_url)
      VALUES (${sessionTitle}, ${image_url})
      RETURNING id
    `;

    // Insert each exercise into the session_exercise table
    await Promise.all(
      exercises.map(exerciseId =>
        sql`INSERT INTO session_exercise (session_id, exercise_id) VALUES (${session[0].id}, ${exerciseId})`
      )
    );

    res.json({ success: true });
  } catch (err) {
    console.error('Error creating session:', err);
    res.status(500).send('Server error');
  }
});

// Route to fetch images
app.get('/get-images', async (req, res) => {
  try {
    const images = await sql`SELECT url FROM images`; // Assuming `sql` is your postgres connection
    const urls = images.map(image => image.url); // Extract just the URL
    res.json(urls); // Send the array of URLs
  } catch (error) {
    console.error('Failed to fetch images:', error);
    res.status(500).send('Server error');
  }
});



// Backend endpoint for fetching exercises of a session
app.get('/get-session-exercises', async (req, res) => {
  const { sessionId } = req.query;
  try {
      const exercises = await sql`SELECT e.* FROM exercise e
          JOIN session_exercise se ON e.id = se.exercise_id
          WHERE se.session_id = ${sessionId}`;
      res.json(exercises);
  } catch (err) {
      console.error(err);
      res.status(500).send('Server error');
  }
});

// Updated route to save session activity
app.post('/save-activity', async (req, res) => {
  const { sessionId, duration, notes , date} = req.body;
  try {
      // Insert the activity into the activities table
      await sql`
          INSERT INTO activities (session_id, duration, notes , date)
          VALUES (${sessionId}, ${duration}, ${notes} , ${date})
      `;
      res.json({ success: true });
  } catch (err) {
      console.error(err);
      res.status(500).send('Server error');
  }
});


app.get('/get-session/:id', async (req, res) => {
  const { id } = req.params;
  try {
      const session = await sql`SELECT * FROM session WHERE id = ${id}`;
      res.json(session);
  } catch (err) {
      console.error(err);
      res.status(500).send('Server error');
  }
});

app.get('/get-activities', async (req, res) => {
  try {
      const activities = await sql`
          SELECT a.*, s.name AS session_name, s.image_url AS session_image
          FROM activities a
          JOIN session s ON a.session_id = s.id
      `;
      res.json(activities);
  } catch (err) {
      console.error(err);
      res.status(500).send('Server error');
  }
});
