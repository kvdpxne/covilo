import { User } from "./user"
import { CommentReactions } from "./comment-reaction"

export interface Comment {
  author: User
  content: string
  reactions: CommentReactions
  pinned: boolean
}

export declare type Comments = Comment[]

