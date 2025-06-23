#include <iostream>#include <iostream>
using namespace std;
int main() {
    int a1, a2, a3;

    cout << "Enter 2 angles of the Triangle: ";
    cin >> a1 >> a2;

    a3 = 180 - (a1 + a2);

    cout << "The 3rd angle is: " << a3 << endl;

    return 0;
}

