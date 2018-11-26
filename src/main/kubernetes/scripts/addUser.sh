if [[ $# -eq 0 ]] ; then
    echo 'You must provide one argument for the password of the "main_admin" user to be created'
    echo '  Usage:  configure_repset_auth.sh MyPa55wd123'
    echo
    exit 1
fi

# Initiate replica set configuration
echo "Configuring the MongoDB Replica Set"
kubectl exec clbapp-project-c86849fdb-dngjk -c mongod-container -- mongo --eval 'rs.initiate({_id: "MainRepSet", version: 1, members: [ {_id: 0, host: "127.0.0.1:27017"}]});'

# Wait a bit until the replica set should have a primary ready
echo "Waiting for the Replica Set to initialise..."
sleep 30
kubectl exec clbapp-project-c86849fdb-dngjk -c mongod-container -- mongo --eval 'rs.status();'

# Create the admin user (this will automatically disable the localhost exception)
echo "Creating user: 'admin'"
kubectl exec clbapp-project-c86849fdb-dngjk -c mongod-container -- mongo --eval 'db.getSiblingDB("admin").createUser({user:"admin",pwd:"'"${1}"'",roles:[{role:"root",db:"admin"}]});'
echo "User 'admin' created! Creating user for clb database with password '"${1}"'"
kubectl exec clbapp-project-c86849fdb-dngjk -c mongod-container -- mongo admin -u admin -p ${1} --eval 'db.getSiblingDB("clb").createUser({user:"admin",pwd:"'"${1}"'",roles:[{role:"root",db:"admin"}]});'
echo "User Created on clb database!"


