# requirments 
install kubectl , docker , helm and minikube in your machine 

https://kubernetes.io/docs/tasks/tools/install-kubectl-windows/
https://helm.sh/docs/intro/install/
https://minikube.sigs.k8s.io/docs/start/
https://docs.docker.com/docker-for-windows/install/

## minikube 

start the minikube 

minikube start 

in a differant terminal run 

sudo minikube tunnel

## deploy postgresql 


helm repo add bitnami https://charts.bitnami.com/bitnami
helm install postgres bitnami/postgresql --set global.postgresql.postgresqlPassword=test@1234



PostgreSQL can be accessed via port 5432 on the following DNS name from within your cluster:

    postgres-postgresql.default.svc.cluster.local - Read/Write connection

To get the password for "postgres" run:

    export POSTGRES_PASSWORD=$(kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.postgresql-password}" | base64 --decode)

To connect to your database run the following command:

    kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:11.11.0-debian-10-r62 --env="PGPASSWORD=$POSTGRES_PASSWORD" --command -- psql --host postgres-postgresql -U postgres -d postgres -p 5432



To connect to your database from outside the cluster execute the following commands:

    kubectl port-forward --namespace default svc/postgres-postgresql 5432:5432 &
    PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U postgres -d postgres -p 5432


To connect to your database from outside the cluster execute the following commands:

    kubectl port-forward --namespace default svc/postgres-postgresql 5432:5432 &
    PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U postgres -d postgres -p 5432


## deploy kafka 

helm repo add bitnami https://charts.bitnami.com/bitnami
helm install kafka bitnami/kafka

Kafka can be accessed by consumers via port 9092 on the following DNS name from within your cluster:

    kafka.default.svc.cluster.local

Each Kafka broker can be accessed by producers via port 9092 on the following DNS name(s) from within your cluster:

    kafka-0.kafka-headless.default.svc.cluster.local:9092

To create a pod that you can use as a Kafka client run the following commands:

    kubectl run kafka-client --restart='Never' --image docker.io/bitnami/kafka:2.7.0-debian-10-r109 --namespace default --command -- sleep infinity
    kubectl exec --tty -i kafka-client --namespace default -- bash

    PRODUCER:
        kafka-console-producer.sh \
            
            --broker-list kafka-0.kafka-headless.default.svc.cluster.local:9092 \
            --topic test

    CONSUMER:
        kafka-console-consumer.sh \
            
            --bootstrap-server kafka.default.svc.cluster.local:9092 \
            --topic test \
            --from-beginning


# build apps 

mvn package -DskipTests 
eval $(minikube docker-env)
docker build -t api-gateway:0.0.1 .

# deploy 
cd helm-chart
helm upgrade --install apps .