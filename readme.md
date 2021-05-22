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

