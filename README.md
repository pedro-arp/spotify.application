<h1>Spotify API Integration Application</h1>
    
   <h2>Overview</h2>
   <p>
        This application serves as a RESTful API wrapper for interacting with Spotify's Web API. 
        It provides endpoints to fetch access tokens and retrieve album details using the Spotify API.
    </p>
    
   <h2>Features</h2>
    <ul>
        <li><strong>Token Generation Endpoint:</strong> Obtain an access token for the Spotify API using the Client Credentials Flow.</li>
        <li><strong>Album Details Retrieval:</strong> Fetch album information, including artists, by providing the album ID.</li>
    </ul>
    
   <h2>Prerequisites</h2>
    <ul>
        <li>Java 21</li>
        <li>Maven for dependency management and project builds</li>
        <li>Docker to run integration tests</li>
        <li>Spotify Developer Account to obtain <code>CLIENT_ID</code> and <code>CLIENT_SECRET</code>.</li>
    </ul>
    
   <h2>Configuration</h2>
    <p>The application requires the following environment variables:</p>
    <ul>
        <li><strong>BEARER_TOKEN:</strong> The token used for API authentication.</li>
        <li><strong>CLIENT_ID:</strong> Your Spotify API client ID.</li>
        <li><strong>CLIENT_SECRET:</strong> Your Spotify API client secret.</li>
    </ul>
    
   <h3>YAML Configuration (<code>application.yml</code>)</h3>
    <pre>
<code>
server:
  port: 8080
  error:
    include-stacktrace: never

variables:
  bearer-token: ${BEARER_TOKEN}
spotify-api:
  token:
    token-base-url: https://accounts.spotify.com/
    uri-token: api/token
    grant-type: client_credentials
    client-id: ${CLIENT_ID:YOUR_CLIENT_ID}
    client-secret: ${CLIENT_SECRET:YOUR_CLIENT_SECRET}
  base-url: https://api.spotify.com/v1/
  uri-albums: albums/{albumId}
</code>
    </pre>
    
   <h2>Endpoints</h2>
    
   <h3>Base Path</h3>
    <p>All endpoints are prefixed with <code>/v1/spotify-api</code>.</p>
    
   <h3>1. Generate Token</h3>
   <ul>
        <li><strong>Endpoint:</strong> <code>/v1/spotify-api/token</code></li>
        <li><strong>Method:</strong> POST</li>
        <li><strong>Response:</strong> Returns a generated token.</li>
    </ul>
        <h4>Example Request:</h4>
    <ul>
        <li><strong>POST:</strong><code>http://localhost:8080/v1/spotify-api/token</code></li>
    </ul>
    <h4>Example Response:</h4>
    <pre>
<code>
{
  "access_token": "BQD...XYZ",
  "token_type": "Bearer",
  "expires_in": 3600
}
</code>
    </pre>
    
  <h3>2. Get Album Details</h3>
    <ul>
        <li><strong>Endpoint:</strong> <code>/v1/spotify-api/{albumId}</code></li>
        <li><strong>Method:</strong> GET</li>
        <li><strong>Path Variable:</strong> 
            <ul>
                <li><code>albumId:</code> Spotify's unique ID for the album</li>
            </ul>
        </li>
        <li><strong>Response:</strong> Returns details about the specified album.</li>
    </ul>
    <h4>Example Request:</h4>
    <ul>
        <li><strong>GET:</strong><code>http://localhost:8080/v1/spotify-api/4aawyAB9vmqN3uQ7FjRGTy</code></li>
    </ul>
    <h4>Example Response:</h4>
    <pre>
<code>
{
  "id": "4aawyAB9vmqN3uQ7FjRGTy",
  "name": "Album Name",
  "release_date": "2024-01-01",
  "artists": [
    {
      "id": "1vCWHaC5f2uS3yhpwWbIA6",
      "name": "Artist Name"
    }
  ],
  "tracks": [
    {
      "id": "6rqhFgbbKwnb9MLmUQDhG6",
      "name": "Track Name",
      "duration_ms": 240000
    }
  ]
}
</code>
    </pre>
    
  <h2>Implementation</h2>
  <h3>Controller (<code>SpotifyApiController.java</code>)</h3>
      
   <h2>How to Run the Application</h2>
   <ol>
        <li>Clone the repository.</li>
        <li>Configure environment variables or update <code>application.yml</code> with your Spotify credentials.</li>
        <li>Build the application using Maven:
            <pre><code>mvn clean install</code></pre>
        </li>
    </ol>
    
   <h2>References</h2>
    <ul>
        <li><a href="https://developer.spotify.com/documentation/web-api" target="_blank">Spotify Web API Documentation</a></li>
    </ul>

   <p>Enjoy building with the Spotify API! 🎵</p>
