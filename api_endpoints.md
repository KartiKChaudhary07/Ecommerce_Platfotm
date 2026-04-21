# BookNest API Endpoints List

## Auth Module (`/api/v1/auth`)
- `POST /signup`: Register a new user (Customer/Admin).
- `POST /signin`: Login and receive JWT.

## Book Module (`/api/v1/books`)
- `GET /`: Get all books (Paginated).
- `GET /{id}`: Get book details.
- `GET /search?query={q}`: Search books by title.
- `GET /featured`: Get featured books.
- `POST /`: Create book (Admin only).
- `PUT /{id}`: Update book (Admin only).
- `DELETE /{id}`: Delete book (Admin only).

## Cart Module (`/api/v1/cart`)
- `GET /`: View current user's cart.
- `POST /add?bookId={id}&quantity={n}`: Add/Update item in cart.
- `PUT /update/{id}?quantity={n}`: Update item quantity.
- `DELETE /remove/{id}`: Remove item from cart.
- `DELETE /clear`: Clear cart.

## Order Module (`/api/v1/orders`)
- `POST /place?shippingAddress={addr}&paymentMethod={COD|WALLET}`: Place order.
- `GET /my-orders`: View user order history.
- `GET /all`: View all orders (Admin only).
- `PUT /{id}/status?status={STATUS}`: Update order status (Admin only).

## Wallet Module (`/api/v1/wallet`)
- `GET /`: View wallet balance.
- `POST /add?amount={n}`: Add money to wallet.
- `GET /transactions`: View wallet transaction history.

## Review Module (`/api/v1/reviews`)
- `POST /add?bookId={id}&rating={1-5}&comment={msg}`: Submit review.
- `GET /book/{bookId}`: Get reviews for a book.
- `GET /book/{bookId}/average`: Get average rating.

## Wishlist Module (`/api/v1/wishlist`)
- `GET /`: View wishlist.
- `POST /add?bookId={id}`: Add book to wishlist.
- `DELETE /remove/{id}`: Remove from wishlist.
- `POST /move-to-cart/{id}`: Move wishlist item to shopping cart.

## Notification Module (`/api/v1/notifications`)
- `GET /`: View user notifications.
- `PUT /{id}/read`: Mark notification as read.
- `PUT /read-all`: Mark all as read.
