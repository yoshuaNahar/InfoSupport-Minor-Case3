environment {
    AWS_ACCESS_KEY_ID     = credentials('AWS_ACCESS_KEY_ID')
    AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
}

pipeline {
  agent any

  stages {
    stage('Clean') {
      steps {
		sh 'mvn clean'
      }
    }

    stage('SonarQube analysis') {
	  steps {
		  withSonarQubeEnv('SonarQube') {
			  // requires SonarQube Scanner for Maven 3.2+
			  sh 'mvn org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'
			  sh 'mvn org.pitest:pitest-maven:mutationCoverage'
			  sh 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar -Dsonar.pitest.mode=reuseReport'
	  	  }
	  }
    }
	
	stage('Package') {
	  steps {
	    sh 'mvn package'
	  }
	}

    stage('Docker') {
	  when {
		branch 'master'
	  }
      steps {
          // Clear previous deployment
          sh 'docker-compose -f docker-compose.deployment.yml stop'
          sh 'docker-compose -f docker-compose.deployment.yml down --remove-orphans'
          sh 'docker-compose -f docker-compose.deployment.yml rm -f'

          // Lift
          // sh 'MYSQL_HOST=jeruno-mysql MYSQL_PORT=3306 RABBIT_HOST=jeruno-rabbitmq RABBIT_PORT=5672 microservices_bs_klant_beheer_url=http://jeruno-spring-bs_klant_beheer:8920 microservices_bs_voertuig_beheer_url=http://jeruno-spring-bs_voertuig_beheer:8921 microservices_is_rijksdienst_wegverkeer_url=http://jeruno-spring-is_rijksdienst_wegverkeer:8923 microservices_pcs_onderhoud_url=http://jeruno-spring-pcs_onderhoud:8924 docker-compose -f docker-compose.deployment.yml up --build -d'
          sh 'docker-compose -f docker-compose.deployment.yml up --build -d'
	  }
    }

  }
}
