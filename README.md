# GoTogether - Kotlin Ride-Sharing Application

GoTogether is a Kotlin-based ride-sharing app that leverages Google Maps APIs to provide a seamless carpooling experience. By matching drivers with passengers traveling along similar routes, GoTogether promotes efficient and eco-friendly travel, reducing traffic congestion and emissions.

## Features

- **Real-Time Matching**: Connects drivers and passengers with overlapping routes.
- **Interactive Maps**: Displays routes, pickups, and drop-offs using Google Maps.
- **Gamification Elements**: Earn Impact Scores, badges, and compete on leaderboards to encourage sustainable travel.
- **User Profiles**: View ride history, achievements, and personal statistics.
- **Intuitive UI**: Built with Jetpack Compose for a smooth and modern user experience.

## Getting Started

### Prerequisites

- **Android Studio**: Version Arctic Fox or newer.
- **Android Device or Emulator**: Running Android API level 21 or higher.
- **Google Maps API Key**: Required for map functionalities.
- **Internet Connection**: For accessing map data and network operations.

### Installation

1. **Clone the Repository**

   ```bash
   git clone https://github.com/Alex1lazu/GoTogether.git
   ```

2. **Open the Project in Android Studio**

    - Launch Android Studio.
    - Navigate to **File > Open** and select the cloned project directory.

3. **Configure API Key**

    - Obtain a Google Maps API key from the [Google Cloud Console](https://console.cloud.google.com/).
    - In your project's `local.properties` file, add:

      ```makefile
      MAPS_API_KEY=YOUR_API_KEY_HERE
      ```

4. **Build and Run**

    - Sync the project with Gradle files.
    - Select your device or emulator.
    - Click the **Run** button to build and launch the app.

## Usage

1. **Launch the App**

    - Choose your role: **Driver** or **Passenger**.

2. **Driver Mode**

    - **Set Up Your Ride**: Enter your starting location, destination, and departure time.
    - **View Potential Passengers**: See a list of passengers along your route with their profiles and ratings.
    - **Accept Requests**: Select passengers you'd like to pick up.
    - **Start Your Journey**: Follow the optimized route provided by the app.

3. **Passenger Mode**

    - **Request a Ride**: Enter your pickup location and destination.
    - **Find Drivers**: View available drivers heading your way.
    - **Send Ride Request**: Choose a driver and send a request.
    - **Track Your Ride**: Once accepted, monitor the driver's location in real-time.

4. **Profile and Gamification**

    - **Impact Score**: Earn points for eco-friendly actions like carpooling.
    - **Badges and Achievements**: Unlock rewards for reaching milestones.
    - **Leaderboard**: Compete with others to promote sustainable travel.
    - **View Your Profile**: Access your ride history, achievements, and personal statistics.

## Technologies Used

- **Kotlin**: Primary programming language.
- **Jetpack Compose**: For building declarative UI components.
- **Google Maps SDK for Android**: Provides mapping functionalities.
- **Ktor Client**: For network operations.
- **Coroutines**: For asynchronous programming.
- **Material Design Components**: Ensures a consistent and modern UI.

## Contact

- **Name**: Alex Lazureanu
- **Email**: [lazureanualex@gmail.com](mailto:lazureanualex@gmail.com)
- **LinkedIn**: [Alex Lazureanu](https://www.linkedin.com/in/alex-lazureanu)
- **GitHub**: [Alex1lazu](https://github.com/Alex1lazu)