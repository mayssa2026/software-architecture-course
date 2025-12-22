org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/stock/1'
    }
    response {
        status 200
        headers {
            contentType(applicationJson())
        }
        body(8)
    }
}
