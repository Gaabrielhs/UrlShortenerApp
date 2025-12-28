package com.example.urlshortenerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.urlshortenerapp.R
import com.example.urlshortenerapp.ui.preview.LinksListStateProvider
import com.example.urlshortenerapp.ui.theme.UrlShortenerAppTheme

object LinkScreenInputTags {
    const val TEXT_FIELD = "LinkScreen_TextField"
    const val BUTTON = "LinkScreen_Button"
    const val LOADING_INDICATOR = "LinkScreen_LoadingIndicator"
    const val ERROR_MESSAGE = "LinkScreen_ErrorMessage"
}

@Composable
fun LinksScreenInput(modifier: Modifier = Modifier, state: LinksListState, actions: LinksListActions) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1.0f).testTag(LinkScreenInputTags.TEXT_FIELD),
                value = state.inputValue,
                onValueChange = actions::onInputValueChanged,
                isError = state.status.isError,
                readOnly = state.status.isLoading,
                placeholder = {
                    Text(text = stringResource(R.string.input_placeholder))
                },
                trailingIcon = {
                    if (state.status.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(
                                24.dp
                            ).testTag(LinkScreenInputTags.LOADING_INDICATOR)
                        )
                    } else {
                        IconButton(
                            modifier = Modifier.testTag(LinkScreenInputTags.BUTTON),
                            onClick = actions::onCommit
                        ) {
                            Icon(
                                painter = rememberVectorPainter(
                                    image = Icons.AutoMirrored.Outlined.Send
                                ),
                                contentDescription = stringResource(
                                    R.string.send_icon_content_description
                                )
                            )
                        }
                    }
                },
                supportingText = state.status.error?.let {
                    {
                        Text(
                            modifier = Modifier.testTag(LinkScreenInputTags.ERROR_MESSAGE),
                            text = it.message
                        )
                    }
                },
                keyboardActions = KeyboardActions(
                    onAny = { actions.onCommit() }
                ),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Send
                )
            )
        }
    }
}

@Preview
@Composable
private fun LinksScreenInputPreview(@PreviewParameter(LinksListStateProvider::class) state: LinksListState) {
    UrlShortenerAppTheme {
        LinksScreenInput(
            state = state,
            actions =
            object : LinksListActions {
                override fun onInputValueChanged(input: String) {}

                override fun onCommit() {}
            }
        )
    }
}
