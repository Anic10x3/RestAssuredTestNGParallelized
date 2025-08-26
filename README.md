🚀 API Automation Framework

A Java-based API Automation Framework built with Rest Assured, TestNG, and Extent Reports.
The framework is designed for scalability, parallel execution, and secure logging (sensitive data like tokens are masked) and you get reports in slack.


📌 Features

✅ Centralized Request Builder → Supports GET, POST, PUT, DELETE with headers, query params & body.

✅ Authentication Handling → Automatic token management with masked logging.

✅ Parallel Test Execution → Powered by TestNG + ThreadLocal.

✅ Advanced Reporting → Extent Reports integration with request/response logging and reports are sent to slack right away.

✅ Configurable Setup → Base URL, timeouts, and tokens configurable via config.properties.

✅ Secure Logging → Tokens and sensitive info masked in console and reports.

✅ Tag wise test run -> Run your test based on tags as well

✅ Github Actions-> Power to run your test on any pull request raised, pull request merged, scheduling the tests to run at specific time and uploading the test artificats


⚙️Tech Stack

Java 11

Rest Assured → API testing

TestNG → Test runner & parallel execution

Extent Reports → Rich HTML reporting

Maven → Dependency management

📦 Installation & Setup
Clone the repo
```bash
git clone https://github.com/your-username/api-automation-framework.git
cd api-automation-framework
```

Install dependencies
```bash
mvn clean install
```

Run test
```bash
mvn clean test
OR
Right click on testng.xml and run test
```

Run test in parallel
```bash
Change thread-count in testng.xml file
```


Screenshots
Github Actions:
Run Workflow:
<img width="1883" height="753" alt="image" src="https://github.com/user-attachments/assets/046edbb0-e3ba-4bf4-ac92-42128310e7b9" />

Test results:
<img width="1852" height="836" alt="image" src="https://github.com/user-attachments/assets/98e463d0-8ba8-41ec-8fe7-0b1c4f4550b2" />

Reports sent to slack:
<img width="1377" height="405" alt="image" src="https://github.com/user-attachments/assets/2d2848de-e9a4-4c26-9122-9bf6cc39f9f4" />

Extent Repor generated:
<img width="1902" height="632" alt="image" src="https://github.com/user-attachments/assets/3c32f6f9-c741-404f-bd44-8ddb1028ee19" />

Authors:
- [@AniketK Khaire](https://www.linkedin.com/in/aniket-khaire-sdet/)



