FROM oven/bun:latest AS build-stage

WORKDIR /app

COPY package.json ./
COPY bun.lockb ./

RUN bun install

COPY . .

RUN mv stack.env .env
RUN bun run build

# Stage 2: Serve the Vite app with nginx
FROM nginx:alpine AS production-stage

# Copy the built app from the previous stage to nginx public directory
COPY --from=build-stage /app/dist /usr/share/nginx/html

# Copy nginx configuration
COPY ./nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80 to the outside world
EXPOSE 80

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
