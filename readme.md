# requirments 
install go , kubectl and docker in your machine 

# install kind in your machine 

## On Linux:

curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.11.0/kind-linux-amd64
chmod +x ./kind
mv ./kind /some-dir-in-your-PATH/kind
COPY
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.11.0/kind-linux-amd64
chmod +x ./kind
mv ./kind /some-dir-in-your-PATH/kind

## On Mac (homebrew):

brew install kind
COPY
brew install kind
or
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.11.0/kind-darwin-amd64
COPY
curl -Lo ./kind https://kind.sigs.k8s.io/dl/v0.11.0/kind-darwin-amd64

## On Windows:

curl.exe -Lo kind-windows-amd64.exe https://kind.sigs.k8s.io/dl/v0.11.0/kind-windows-amd64
Move-Item .\kind-windows-amd64.exe c:\some-dir-in-your-PATH\kind.exe
COPY
curl.exe -Lo kind-windows-amd64.exe https://kind.sigs.k8s.io/dl/v0.11.0/kind-windows-amd64
Move-Item .\kind-windows-amd64.exe c:\some-dir-in-your-PATH\kind.exe
On Windows via Chocolatey (https://chocolatey.org/packages/kind)

choco install kind
COPY
choco install kind

## create cluster 
kind create cluster --config kind-config.yaml


## deploy postgresql 


helm repo add bitnami https://charts.bitnami.com/bitnami
helm install postgres bitnami/postgresql -f PATH

PostgreSQL can be accessed via port 5432 on the following DNS name from within your cluster:

    postgres-postgresql.default.svc.cluster.local - Read/Write connection

To get the password for "postgres" run:

    export POSTGRES_PASSWORD=$(kubectl get secret --namespace default postgres-postgresql -o jsonpath="{.data.postgresql-password}" | base64 --decode)

To connect to your database run the following command:

    kubectl run postgres-postgresql-client --rm --tty -i --restart='Never' --namespace default --image docker.io/bitnami/postgresql:11.11.0-debian-10-r67 --env="PGPASSWORD=$POSTGRES_PASSWORD" --command -- psql --host postgres-postgresql -U postgres -d postgres -p 5432


To connect to your database from outside the cluster execute the following commands:

    kubectl port-forward --namespace default svc/postgres-postgresql 5432:5432 &
    PGPASSWORD="$POSTGRES_PASSWORD" psql --host 127.0.0.1 -U postgres -d postgres -p 5432

