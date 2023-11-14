FROM node:latest

# Set the working directory
WORKDIR /opt/liferay

# Copy the application code into the container
COPY . .

# Run npm install as root
RUN npm install

# Expose the necessary port (if needed)
EXPOSE 80

# Run your Node.js application as root
CMD ["node", "app.js"]
