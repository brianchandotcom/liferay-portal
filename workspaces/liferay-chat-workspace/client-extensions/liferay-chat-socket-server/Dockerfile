# Use Node 16 alpine as parent image
FROM node:16-alpine

# Change the working directory on the Docker image to /app
WORKDIR /app

# Copy package.json and package-lock.json to the /app directory
COPY /static/ ./

# Install dependencies
RUN npm install

# Copy the rest of project files into this image
#COPY ./build/liferay-client-extension-build/static

# Expose application port
EXPOSE 80
EXPOSE 3000

# Start the application
CMD npm start
