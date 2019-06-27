***Settings***
Library    RequestsLibrary

***Variables***
${url}    http://localhost:9999/mock
${uri}    /test

***Test Cases***
TC01 Request to mock server case success
    ${header}    get header    1234
    ${res}    get request from ${url}, ${uri} and ${header}
    should be equal as integers    ${res.status_code}    200
    should be equal as integers    ${res.json()}[status][code]    0
    should be equal    ${res.json()}[status][message]    success

TC02 Request to mock server case fail
    ${header}    get header    5678
    ${res}    get request from ${url}, ${uri} and ${header}
    should be equal as integers    ${res.status_code}    404
    should be equal as integers    ${res.json()}[status][code]    1
    should be equal    ${res.json()}[status][message]    fail

***Keywords***
get request from ${url}, ${uri} and ${header}
    create session    api    ${url}
    ${res}   get request    api    ${uri}    headers=${header}
    log    ${res.json()}
    return from keyword    ${res}

get header
    [Arguments]    ${token}    
    ${data}    create dictionary    token=${token}
    log    ${data}
    return from keyword    ${data}

