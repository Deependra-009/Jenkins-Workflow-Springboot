# 🚀 Jenkins [GitHUb Workflows in Sprinboot] Guide

📌 Steps in This Workflow
✅ Runs on push and pull request events
✅ Checks out the code
✅ Sets up Java & Maven
✅ Builds the project (mvn clean package)
✅ Runs Unit & Integration Tests (mvn test)
✅ Blocks Merge if tests fail

All Test passed should be passed before push code and merge in the main branch (eg. In this project main branch is protective) 

File Name: .github/workflows/ci-cd-pipeline.yml

### Pre-Push Hook (Run Tests Before Pushing)


Navigate to your Git repository:
```
cd your-repo/.git/hooks
```

Create or edit the pre-push hook:
```
nano pre-push
```

Add the following script (example for a Java project with Maven):
```
#!/bin/sh

echo "🚀 Running Pre-Push Checks..."

# Build the project
mvn clean package -DskipTests
if [ $? -ne 0 ]; then
    echo "❌ Build failed! Push aborted."
    exit 1
fi

# Run Unit Tests
mvn test
if [ $? -ne 0 ]; then
    echo "❌ Unit Tests failed! Push aborted."
    exit 1
fi

# Run Integration Tests
mvn verify
if [ $? -ne 0 ]; then
    echo "❌ Integration Tests failed! Push aborted."
    exit 1
fi


echo "✅ All checks passed! Proceeding with push..."
exit 0


```
Save and give execution permissions:

```
chmod +x pre-push
```


### If you getting this error:

```
deependra@deependra:~/Code/SpringBoot/Jenkins-Workflow$ mvn test
[ERROR] Error executing Maven.
[ERROR] java.lang.IllegalStateException: Unable to load cache item
[ERROR] Caused by: Unable to load cache item
[ERROR] Caused by: Could not initialize class com.google.inject.internal.cglib.core.$MethodWrapper
[ERROR] Caused by: Exception com.google.inject.internal.cglib.core.$CodeGenerationException: java.lang.reflect.InaccessibleObjectException-->Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain) throws java.lang.ClassFormatError accessible: module java.base does not "opens java.lang" to unnamed module @69a10787 [in thread "main"]
```

This error typically occurs due to **Java module access restrictions** when running Maven tests. It's likely caused by the **JDK version** being used with an incompatible **Maven or Guice version**.  

### **Possible Solutions**  

#### ✅ **1. Check Java & Maven Compatibility**  
Run the following to check your Java and Maven versions:  
```bash
java -version
mvn -version
```
- If you are using **Java 17+**, try switching to **Java 11** (some dependencies may not work well with Java 17+).  
- If Maven is outdated, update it to the latest stable version.  

#### ✅ **2. Add JVM Arguments**  
Modify your **MAVEN_OPTS** environment variable to allow access to restricted modules:  
```bash
export MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED"
mvn clean test
```

Alternatively, pass this argument directly when running Maven:  
```bash
mvn clean test -DargLine="--add-opens java.base/java.lang=ALL-UNNAMED"
```

#### ✅ **3. Exclude Guice from Dependencies**  
If you are using **Google Guice**, try excluding its conflicting version:  

In **pom.xml**:  
```xml
<dependency>
    <groupId>com.google.inject</groupId>
    <artifactId>guice</artifactId>
    <version>5.1.0</version>  <!-- Ensure it's an updated version -->
</dependency>
```

Or exclude it from Maven plugins:  
```xml
<dependency>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.0.0-M7</version>
    <exclusions>
        <exclusion>
            <groupId>com.google.inject</groupId>
            <artifactId>guice</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

#### ✅ **4. Clear Maven Cache & Rebuild**  
Try deleting the cache and rebuilding the project:  
```bash
rm -rf ~/.m2/repository/com/google/inject
mvn clean install
```

#### ✅ **5. Use an Older JDK Version (If Necessary)**  
If you are using **Java 17+**, and the issue persists, try using **Java 11**:  
```bash
sudo update-alternatives --config java
```
Then, select Java 11.  

---

### **Final Recommendation**  
Start with **Step 2 (JVM arguments), then Step 4 (clear cache), and finally Step 5 (Java version change)** if the issue persists. Let me know if you need further debugging! 🚀
