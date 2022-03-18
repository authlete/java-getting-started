// contextPath is from context.jsp
const baseUrl = contextPath + '/api';

function getCurrentCustomer() {
    return fetch(`${baseUrl}/currentCustomer`)
        .then(response => { return response.json(); })
        .catch(error => {
            console.log('Error getting current customer: ' + error);
            throw error;
        });
}

function getCustomer(customerId) {
    return fetch(`${baseUrl}/customers/${customerId}`)
    .then(response => { return response.json(); })
    .catch(error => {
        console.log('Error getting customer details: ' + error);
        throw error;
    });
}

function redeemPoints(account, amount) {
    const transaction = {
        "amount": -amount,
        "transactionType": "Cash Redemption"
    }
    return fetch(`${baseUrl}/accounts/${account.accountNumber}/transactions`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(transaction),
    }).catch(error => {
        console.log('Error posting transaction: ' + error);
        throw error;
    });
}

function hideLoadingScreen() {
    document.getElementById('container').classList.remove("hidden");
    document.getElementById('dimScreen').classList.add("hidden");
}