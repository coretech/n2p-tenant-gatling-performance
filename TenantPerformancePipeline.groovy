
// Param from JENKINS
appBuildNumber = "${env.BUILD_NUMBER}"
users = "${users}"
during = "${during}"

// Vars for Job Status
buildStatus = "BUILD_STARTED"
successfulStatus = "SUCCESS"
failedStatus = "FAILURE"
abortedStatus = "ABORTED"
jobStatus = failedStatus // By default

def reportLink = "http://idtq-automation.awsqa.idt.net/xxx/${appBuildNumber}"

// Commands
buildCommandToExecute = "mvn -B clean package"
testCommandToExecute = "mvn clean gatling:test -Dusers=${users} -Dduring=${during}"

// Start testing
node("master") {

    try {
        stage('Run the tests') {
            withEnv(["JAVA_HOME=${tool 'jdk8'}", "PATH+MAVEN=${tool 'mvn4.0.0'}/bin:${env.JAVA_HOME}/bin"]) {

                // sh "java -version"
                // sh "mvn -v"
                sh "echo 'STARTING...'"
                sh "${buildCommandToExecute}"
                sh "${testCommandToExecute}"
            }
            jobStatus = successfulStatus
        }
    } catch (InterruptedException e) {
        sh "echo 'INTERRUPTED..............'"
        jobStatus = abortedStatus

        throw e
    } catch (error) {
        sh "echo 'FAILED........................:$error'"
        jobStatus = failedStatus

    } finally {
        sh "echo 'FINALLY.................'"
    }

    stage('Publish Report') {
        echo 'Publishing Report.......................'
        gatlingArchive()
    }
}
