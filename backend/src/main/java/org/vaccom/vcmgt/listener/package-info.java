/**
 * This package is used for event based processing. <br>
 * The message is published via {@link org.springframework.context.ApplicationEventPublisher} and handling in this package.
 * In case distributed event then developer should update the handler here and dispatch the message to a middleware <br>
 * This also eliminates the code base change.
 *
 * @author TrinhNX
 * @since 8/29/2021
 */
package org.vaccom.vcmgt.listener;