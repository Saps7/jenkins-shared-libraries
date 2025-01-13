def checkoutSCM(String branch = '*/master', String repoUrl) {
    echo "Checking out code from ${repoUrl} (branch: ${branch})"
    checkout([
        $class: 'GitSCM',
        branches: [[name: branch]],
        extensions: [],
        userRemoteConfigs: [[url: repoUrl]]
    ])
}

def buildDockerImage(String imageName) {
    echo "Building Docker image: ${imageName}"
    sh "docker build -t ${imageName} ."
}

def pushDockerImage(String imageName, String credentialsId, String dockerHubUser) {
    echo "Pushing Docker image: ${imageName}"
    withCredentials([string(credentialsId: credentialsId, variable: 'dockerhubpwd')]) {
        sh """
        docker login -u ${dockerHubUser} -p ${dockerhubpwd}
        docker push ${imageName}
        """
    }
}
