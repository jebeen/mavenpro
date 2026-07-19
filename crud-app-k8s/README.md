# Simple CRUD App — Spring Boot + React + PostgreSQL(already running) on local Kubernetes (Docker Desktop)

## Project structure

crud-app/
├── backend/
├── frontend/
├── k8s/
│   ├── .env                      
│   ├── .gitignore
│   ├── db-credentials.yaml       
│   ├── backend-deployment.yaml
│   └── frontend-deployment.yaml
│   └── update-db-secret.sh
  
Confirm Kubernetes is running

kubectl config use-context docker-desktop
kubectl get nodes 

Steps to deploy and access:
===========================

1. Create .env file in k8s/ with database credential

DB_HOST=<host_name>
DB_PORT=<port>
DB_NAME=<db_name>
DB_USER=<db_user>
DB_PASSWORD=<db_password>


2. Build the docker images for backend and frontend
docker build -t crud-backend:local ./backend
docker build -t crud-frontend:local ./frontend

3. Run the commands
kubectl create secret generic db-credentials --from-env-file=.env
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml

4. Run the script whenever change .env file
sh update-db-secret.sh 

5. Access the app

http://localhost:30080

If NodePort isn't reachable, use port-forward instead:

kubectl port-forward svc/frontend-service 8081:80

then open http://localhost:8081

6. Test it
- Add an item → confirm it shows in the list
- Refresh the page → confirm it persists
- Edit / Delete an item

7. Redeploying after code changes

→ Delete the Kubernetes resources first

bash

kubectl delete secret db-credentials --ignore-not-found
kubectl delete -f k8s/frontend-deployment.yaml
kubectl delete -f k8s/backend-deployment.yaml

→ Confirm they're gone:

bash

kubectl get pods
kubectl get deployments
kubectl get svc

→ Check and Delete the Docker images

bash

docker images | grep crud

Then remove them by name:

bash

docker rmi crud-backend:local
docker rmi crud-frontend:local

If it complains the image is still in use by a stopped container:

bash

docker rmi -f crud-backend:local
docker rmi -f crud-frontend:local

→ Rebuild the images

bash

cd crud-app
docker build -t crud-backend:local ./backend
docker build -t crud-frontend:local ./frontend

Confirm they exist:

bash

docker images | grep crud

→ Reapply the Kubernetes manifests

bash

kubectl delete secret db-credentials
kubectl apply -f k8s/db-credentials.yaml
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/frontend-deployment.yaml

If changes in code backend/frontend, then run the below commands

kubectl rollout restart deployment backend
kubectl rollout restart deployment frontend
