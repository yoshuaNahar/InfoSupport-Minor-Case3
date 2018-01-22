echo "Removing old containers..."
docker-compose down --remove-orphans

echo "Lifting kantilever environment..."
docker login minordotnet.azurecr.io --username "a1236a8c-9c84-4ca3-b15c-6ff8ef6e1d8b" --password "Vqox9jmjWkA6BlkXlaUG"
docker network create proxy_network
docker network create kantilever_network
docker-compose -f docker-compose.kantylever.yml up --build -d

echo "Lifting containers..."
docker-compose -f docker-compose.deployment.yml up --build
