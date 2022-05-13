
// Param from JENKINS
users = "${users}"
during = "${during}"

// Vars for Job Status
successfulStatus = "SUCCESS"
failedStatus = "FAILURE"
abortedStatus = "ABORTED"
jobStatus = failedStatus // By default

// Commands
buildCommandToExecute = "mvn -B clean package"
testCommandToExecute = "mvn clean gatling:test -Dusers=${users} -Dduring=${during}"

// Start testing
node("master") {

    try {
        stage('Run the tests') {
                sh "echo 'STARTING...'"
                sh "${buildCommandToExecute}"
                sh "${testCommandToExecute}"
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
