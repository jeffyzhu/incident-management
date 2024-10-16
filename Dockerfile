# Use the official Node.js image as a base image
FROM node:16-alpine AS build

# Set working directory
WORKDIR /app

# Install app dependencies
COPY package*.json ./
RUN npm install

# Copy the app source code
COPY . .

# Expose port 3000
EXPOSE 3000

# Command to run the app in development mode
CMD ["npm", "start"]