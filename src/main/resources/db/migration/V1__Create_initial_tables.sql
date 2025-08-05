
-- Table pour les Donateurs (Donor)
CREATE TABLE IF NOT EXISTS donor (
                                     id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
    );

-- Table pour les Bénéficiaires (Beneficiary)
CREATE TABLE IF NOT EXISTS beneficiary (
                                           id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255)
    );

-- Table pour les Moyens de Paiement (Payment)
CREATE TABLE IF NOT EXISTS payment (
                                       id VARCHAR(255) PRIMARY KEY,
    payment_type VARCHAR(255) NOT NULL
    );

-- Table pour les Donations (Donation)
CREATE TABLE IF NOT EXISTS donation (
                                        id VARCHAR(255) PRIMARY KEY,
    -- Le montant est nullable car il est récupéré plus tard depuis l'API Vola
    amount DOUBLE PRECISION,
    date_time TIMESTAMP WITH TIME ZONE NOT NULL,
                            status VARCHAR(255) NOT NULL,
    psp_payment_id VARCHAR(255),
    donor_id VARCHAR(255) REFERENCES donor(id),
    payment_id VARCHAR(255) REFERENCES payment(id)
    );

-- Table pour les Aides (Help)
CREATE TABLE IF NOT EXISTS help (
                                    id VARCHAR(255) PRIMARY KEY,
    amount DOUBLE PRECISION NOT NULL,
    date_time TIMESTAMP WITH TIME ZONE NOT NULL,
                            description TEXT,
                            beneficiary_id VARCHAR(255) REFERENCES beneficiary(id)
    );