/*    
 * Created by Adnan Khan <akhan@szellsgroup.com>, Siddheshwar More <simore@szellsds.com> on Oct 2019
*/    
    
@Library("nextgen-jenkins-library@gce-rbs-new-pipeline") _ 
    
GceRbsDeliveryPipeline {    
  //Note: we are explicitly disabling the unit tests here, should not be done    
  //for any other service!!!    
  buildCommand        = "chmod 777 gradlew && ./gradlew build"    
  binaryPath          = "build/libs/*.jar"    
  owaspCommand        = "./gradlew dependencyCheckAnalyze"    
  owaspHtmlReport     = "build/reports" 
  buildJavaVersion    = "jdk11" 
  h2ngFeatureBranch   = "develop"
 
  /*    
  Note: config server has no unit tests and it always fail    
  following is the workaround to not fail the same shared Library    
  pipeline. It will just warn that no reports were found in the logs!    
  */
  unitTestsReportPath = "build/reports/tests/test/**"    
  projectName         = "ng"    
  configServerName    = "config-server" //Not applicable for this service!    
  serviceRegistry     = "h2ng/ng-auth" 
  serviceName         = "ng-auth"
  servicePort         = 8061 
  healthCheck         = "/actuator/health"
  probeDelay          = 60 
   
 
  numOfReplicas       = 1 
  numOfReplicasProd   = 2 
  requestedCpu        = "50m" 
  requestedMemory     = "128Mi" 
  limitCpu            = "2000m" 
  limitMemory         = "1Gi" 
 
  // for hpa 
  cpuPercentForHpa    = 80 
  minForHpa           = 1
  maxForHpa           = 2 
 
  slackChannel        = "#nextgen_build_status"    
  //staging server k8s context    
  stagingServer       = "stage-gce-nextgen-eks-cluster"    
  //production server k8s context    
  productionServer    = "prod-gce-nextgen-eks-cluster"  
} 
