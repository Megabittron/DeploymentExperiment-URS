export interface User {
    _id: {
        $oid: string
    };
    SubjectID: string;
    FirstName: string;
    LastName: string;
    ShirtSize: string;
    Role: string;
}
